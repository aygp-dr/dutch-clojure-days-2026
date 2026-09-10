(ns example.core-test
  (:require [clojure.spec.test.alpha :as stest]
            [clojure.test :refer [deftest is use-fixtures]]
            [dcd.examples]
            [example.core :as sut]))

;; Exercise every s/fdef :args spec while the unit tests run.
(use-fixtures :once
  (fn [f] (stest/instrument) (try (f) (finally (stest/unstrument)))))

(deftest example-function-returns-its-input
  (doseq [v [nil 42 "Dutch Clojure Days" [:talk {:minutes 35}]]]
    (is (= v (sut/example-function v)))))
