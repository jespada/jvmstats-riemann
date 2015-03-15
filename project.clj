(defproject jvmstats-riemann "0.1.0-SNAPSHOT"
  :main jvmstats-riemann.core
  :description "A Clojure daemon designed to monitor jvm stats through jmx"
  :url "https://github.com/jespada/jvmstats-riemann"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/java.jmx "0.2.0"]
                 [org.clojure/tools.cli "0.3.1"]
                 [com.taoensso/timbre "3.0.0-RC4"]
                 [riemann-clojure-client "0.2.9"]])
