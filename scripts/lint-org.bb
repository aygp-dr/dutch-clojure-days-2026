#!/usr/bin/env bb
(ns lint-org
  (:require [babashka.fs :as fs]
            [clojure.java.shell :refer [sh]]
            [clojure.string :as str]))

(defn lint-file [file]
  (print (str "Linting " file " ... "))
  (flush)
  (let [elisp (format "
(require 'org-lint)
(find-file \"%s\")
(let ((res (org-lint)))
  (if res
      (progn
        (princ (format \"\nLint errors in %%s:\n\" \"%s\"))
        (dolist (err res)
          (princ (format \"  Line %%d: %%s\n\" (car err) (nth 2 err))))
        (kill-emacs 1))
    (kill-emacs 0)))" file file)
        cmd ["emacs" "--batch" "--eval" elisp]
        {:keys [exit out err]} (apply sh cmd)]
    (if (zero? exit)
      (println "OK")
      (do
        (println "FAILED")
        ;; Emacs output is often mixed, but 'princ' goes to stdout in batch mode usually
        (println out) 
        (println err)
        (System/exit 1)))))

(defn -main []
  (let [root (str/trim (:out (sh "git" "rev-parse" "--show-toplevel")))]
    (doseq [file (fs/glob root "**/*.org")]
      (lint-file file))
    (println "✓ All files passed org-lint")))

(when (= *file* (System/getProperty "babashka.file"))
  (-main))
