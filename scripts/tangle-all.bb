#!/usr/bin/env bb
(ns tangle-all
  (:require [babashka.fs :as fs]
            [clojure.java.shell :refer [sh]]
            [clojure.string :as str]))

(defn tangle-file [file]
  (println "Processing:" (str file))
  (let [cmd ["emacs" "--batch"
             "--eval" "(require 'org)"
             "--eval" "(setq org-confirm-babel-evaluate nil)"
             "--eval" (format "(org-babel-tangle-file \"%s\")" file)]
        {:keys [exit out err]} (apply sh cmd)]
    (when-not (zero? exit)
      (println "Error tangling" file)
      (println err))))

(defn -main []
  (let [root (str/trim (:out (sh "git" "rev-parse" "--show-toplevel")))]
    (println "Tangling all .org files from" root "...")
    (doseq [file (fs/glob root "**/*.org")]
      (tangle-file file))
    (println "✓ Tangling complete")))

(when (= *file* (System/getProperty "babashka.file"))
  (-main))
