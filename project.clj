(defproject lai "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.taoensso/timbre "3.3.0" :exclusions [org.clojure/clojure]]
                 [com.googlecode.lanterna/lanterna "3.0.0-alpha3"]
                 [org.clojure/core.typed "0.2.68"]
                 [org.clojure/data.finger-tree "0.0.2"]]
  :plugins [[lein-typed "0.3.5"]]
  :main ^:skip-aot lai.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :jvm-opts ["-Xmx4g" "-Djava.awt.headless=true"])
