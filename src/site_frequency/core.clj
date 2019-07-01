(ns site-frequency.core
  (:require [site-frequency.handler :as handler]
            [ring.adapter.jetty :as jetty]
            [site-frequency.bing-client :as bc]))

(defn -main []
  (bc/init-cookie) ;; bing returns empty RSS without cookies
  (jetty/run-jetty
    handler/app
    {:port  3000
     :join? false}))
