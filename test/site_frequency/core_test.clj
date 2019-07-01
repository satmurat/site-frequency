(ns site-frequency.core-test
  (:require [clojure.test :refer :all]
            [site-frequency.handler :as handler]))

(site-frequency.bing-client/init-cookie)

(deftest domain-test
  (is (= "ya.ru" (handler/domain "http://www.ya.ru"))))

(deftest fetch-links-test
  (let [rss (slurp (clojure.java.io/resource "rss.xml"))
        links (handler/fetch-links rss)]
    (is (= 10 (count links)))))

(deftest calc-common-freq-test
  (is (< 5 (count (handler/calc-common-frequency ["clojure" "scala"])))))