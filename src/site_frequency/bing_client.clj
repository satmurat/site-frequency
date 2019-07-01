(ns site-frequency.bing-client
  (:require [clj-http.client :as client]
            [com.climate.claypoole :as cp]
            [config.core :refer [env]])
  (:import (java.util.concurrent Executors)))

(def cs (clj-http.cookies/cookie-store))

(defn init-cookie []
  "Initialize cookies for the upcoming search queries"
  (client/get "https://www.bing.com" {:cookie-store cs}))

; limited pool for bing requests
(def pool (cp/threadpool (:bing-http-max-connections env)))

;(def pool (Executors/newFixedThreadPool 10))

(defn- make-url
  [q]
  (format (:bing-search-rss-url-format env) q))

(defn rss
  "Load rss for query from bing"
  [q]
  (:body (client/get (make-url q) {:cookie-store cs})))

(defn batch-query-rss
  "Performs queries to bing at the pool and returns seq of rss"
  [qs]
  (cp/pmap pool rss qs))

