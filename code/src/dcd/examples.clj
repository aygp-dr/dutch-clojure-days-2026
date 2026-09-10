(ns dcd.examples
  "Loads the org-tangled Clojure examples in examples/ (a classpath root).
  cfp/templates/proposal-template.org tangles its Clojure blocks to
  examples/PROPOSAL_NAME.clj, a file name that doesn't follow the ns-to-path
  convention, so plain `require` can't find it. Requiring this ns loads each
  file once; after that, `(require '[example.core])` is a no-op.")

(def tangled
  "Base names of the tangled files under examples/, in load order."
  ["PROPOSAL_NAME"])

(doseq [base tangled]
  (load (str "/" base)))
