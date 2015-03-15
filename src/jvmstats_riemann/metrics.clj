(ns jvmstats-riemann.metrics
  (require [clojure.java.jmx :as jmx])
  (require [clojure.string :as string])
  (require [clojure.string :refer (split triml)])
  (require [taoensso.timbre :as timbre])
  (:require [riemann.client :refer :all])

  (:gen-class))

(timbre/refer-timbre)

(defn metrics
  [host-jmx port-jmx interval server tags]
  (loop []
    (info (str "Get jvm metrics with jmx running at " host-jmx ":" port-jmx " connection interval " interval))
    ;;Should I try, catch and report as an error to riemann if we can not connect to jmx??
    (jmx/with-connection {:host host-jmx, :port port-jmx}
      (let [metrics {"heapUsage"  (:HeapMemoryUsage (jmx/mbean "java.lang:type=Memory"))
                     "permGenUsage" (:Usage (try
                                              (jmx/mbean "java.lang:type=MemoryPool,name=PS Perm Gen")
                                              (catch Exception e (jmx/mbean "java.lang:type=MemoryPool,name=Perm Gen"))))}
            rclient (tcp-client :host server)]

        (try
          (doseq [[name value] metrics]
            (doseq [datapoints [:committed :init :max :used]]
              (send-event rclient {:service (str "JVM " name datapoints) :metric (get value datapoints)  :description "JVM Stats" :tags (split tags #"\s+")})))
          (catch Exception e (info (str "failed to send event: " e)))
          (finally (close-client rclient)))))

    (Thread/sleep interval)
    (recur)))
