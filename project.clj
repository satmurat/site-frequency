(defproject site-frequency "0.1.0-SNAPSHOT"
  :description "Frequency of sites for search queries"
  :url "https://github.com/satmurat"

  :dependencies [[com.github.kyleburton/clj-xpath "1.4.11"]
                 [com.climate/claypoole "1.1.4"]
                 [org.clojure/clojure "1.10.0"]
                 [ring/ring-defaults "0.2.1"]
                 [ring/ring-json "0.4.0"]
                 [yogthos/config "1.1.4"]
                 [compojure "1.6.1"]
                 [clj-http "3.10.0"]
                 [ring "1.4.0"]]

  :profiles {:test {:resource-paths ["test-resources"]}}

  :clean-targets ^{:protect false} ["target"]

  :main site-frequency.core)
