(defproject lai "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.googlecode.lanterna/lanterna "3.0.0-alpha3"]]
  :main ^:skip-aot lai.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :jvm-opts ["-Xmx4g" "-Djava.awt.headless=true"] )
