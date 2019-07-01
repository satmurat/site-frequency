(ns site-frequency.handler
  (:require [site-frequency.bing-client :as bc]
            [clojure.string :as str]
            [compojure.core :refer [GET defroutes]]
            [ring.middleware.json :as json-middleware]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]])
  (:use [clj-xpath.core :only [$x:text*]]))

(defn domain
  "Returns 2nd level domain from given url"
  [url]
  (let [host (.getHost (clojure.java.io/as-url url))
        parts (str/split host #"\.")]
    (->> parts
         (take-last 2)
         (str/join "."))))

(defn fetch-links
  "Fetches links from rss body"
  [rss]
  ($x:text* "//channel/item/link" rss))

(defn calc-common-frequency
  [qs]
  (->> qs
       bc/batch-query-rss
       (map fetch-links)
       flatten
       distinct     ;; unification links
       (map domain) ;; fetch 2nd level domain
       frequencies))

;; ===== HTTP API ========
(defroutes search-routes
           (GET "/search" req (let [query (get-in req [:params :query])
                                    qs (if (string? query) [query] query)]
                                {:body (calc-common-frequency qs)})))

(def app
  (->
    (wrap-defaults
      #'search-routes
      (assoc-in api-defaults [:responses :content-types] false))
    wrap-reload
    (json-middleware/wrap-json-response {:pretty true})))
