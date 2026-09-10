(ns example.specs-test
  "Generative checks for every pure s/fdef'd fn in the tangled examples.
  Per https://clojure.org/guides/spec (Testing). The examples take arbitrary
  input and carry no domain data yet, so there is no specs ns and no
  data-spec test; add them when a proposal's example grows real data."
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as stest]
            [clojure.test :refer [deftest is testing]]
            [dcd.examples]
            [example.core]))

(def ^:private check-opts {:clojure.spec.test.check/opts {:num-tests 50}})

;; Side-effecting fns: fdef'd for instrumentation, never generatively checked.
;; (none so far)
(def ^:private side-effecting #{})

(defn- checkable []
  (remove side-effecting (stest/enumerate-namespace 'example.core)))

(deftest fdefs-hold-under-generative-testing
  (let [results (stest/check (checkable) check-opts)]
    (is (seq results) "expected at least one fdef'd fn to check")
    (doseq [r results]
      (testing (str (:sym r))
        (is (nil? (:failure r))
            (pr-str (stest/abbrev-result r)))))))

(deftest every-public-fn-has-an-fdef
  ;; The example is a template: a fn added to it should come with its spec.
  (doseq [sym (map (fn [v] (symbol v)) (vals (ns-publics 'example.core)))]
    (testing (str sym)
      (is (some? (s/get-spec sym))))))
