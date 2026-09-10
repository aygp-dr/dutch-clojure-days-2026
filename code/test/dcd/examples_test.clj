(ns dcd.examples-test
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.test :refer [deftest is]]
            [dcd.examples :as examples]))

(deftest every-tangled-example-is-loaded
  ;; A newly tangled proposal (examples/<NAME>.clj) must be listed in
  ;; dcd.examples/tangled, or it would silently go untested.
  (is (= (set examples/tangled)
         (->> (.listFiles (io/file "examples"))
              (map (fn [^java.io.File f] (.getName f)))
              (filter (fn [n] (str/ends-with? n ".clj")))
              (map (fn [n] (subs n 0 (- (count n) 4))))
              set))))

(deftest tangled-namespaces-load
  (is (some? (find-ns 'example.core))))
