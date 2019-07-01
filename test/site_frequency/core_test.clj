(ns site-frequency.core-test
  (:require [clojure.test :refer :all]
            [site-frequency.handler :as handler]))

(site-frequency.bing-client/init-cookie)

(deftest domain-test
  (is (= "ya.ru" (handler/domain "http://www.ya.ru"))))

(deftest uniq-domain-test
  (let [rss (slurp (clojure.java.io/resource "rss.xml"))
        uniq-domains (handler/uniq-domains rss)]
    (is (= 7 (count uniq-domains)))))

(deftest calc-common-freq-test
  (is (< 5 (count (handler/calc-common-frequency ["clojure" "scala"])))))