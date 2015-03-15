(ns jvmstats-riemann.core
  (require [clojure.tools.cli :refer [parse-opts]]
           [clojure.string :as string]
           [jvmstats-riemann.metrics :as metrics])
  (:import (java.net InetAddress))
  (:gen-class))

(def cli-options
 ;;Options required
  [["-h" "--hostname HOST"
    :default (InetAddress/getByName "localhost")
    :parse-fn #(InetAddress/getByName %)]
   ["-p" "--port PORT" "Port number"
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ["-i" "--interval INT" "interval in milliseconds to pull stats"
    :parse-fn #(Integer/parseInt %)]
   ["-s" "--server RIEMANNSERVER"]
   ["-t" "--tags \"jvm_stats graph metrics\""]])

(defn usage [options-summary]
  (->> ["jvmstats-riemann pull jvm stats using jmx and forward to riemann"
        ""
        "Usage: jvmstats-riemann -h localhost -p 2181 -i 5000 -s 10.0.2.1 -t \"jvm_stats graph metrics\""
        ""
        "Options:"
        options-summary
        ""]
       (string/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n")
  (string/join \newline errors))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn -main [& args]
  (let [{:keys [options  arguments errors summary]} (parse-opts args cli-options)]
    (cond
     (:help options) (exit 0 (usage summary))
     (not= (count options) 5) (exit 1 (usage summary)) ;; check for 5 options if not show summary
     errors (exit 1 (error-msg errors)))
    (metrics/metrics (.getHostAddress (:hostname options)) (:port options) (:interval options) (:server options) (:tags options))))
