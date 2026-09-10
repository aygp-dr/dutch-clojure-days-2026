(ns example.core
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as str]))

(defn example-function
  "Description of what this does"
  [input]
  ;; Implementation
  input)

;; Spec the contract (https://clojure.org/guides/spec). In code/, `bb test`
;; runs stest/check on it, so keep it in step with the implementation.
(s/fdef example-function
  :args (s/cat :input any?)
  :ret any?
  :fn (fn [{:keys [args ret]}] (= (:input args) ret)))
