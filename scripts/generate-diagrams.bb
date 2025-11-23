#!/usr/bin/env bb
(ns generate-diagrams
  (:require [babashka.fs :as fs]
            [clojure.java.shell :refer [sh]]
            [clojure.string :as str]))

(defn has-mermaid? [file]
  (str/includes? (slurp (str file)) "begin_src mermaid"))

(defn generate-diagrams [file]
  (println "Processing diagrams in:" (str file))
  (let [cmd ["emacs" "--batch"
             "--eval" "(require 'org)"
             "--eval" "(require 'ob-mermaid)"
             "--eval" "(setq org-confirm-babel-evaluate nil)"
             "--eval" (format "(find-file \"%s\")" file)
             "--eval" "(org-babel-execute-buffer)"]
        {:keys [exit out err]} (apply sh cmd)]
    (when-not (zero? exit)
      (println "Error generating diagrams for" file)
      (println err))))

(defn -main []
  (let [root (str/trim (:out (sh "git" "rev-parse" "--show-toplevel")))]
    (println "Generating Mermaid diagrams from" root "...")
    (fs/create-dirs (fs/path root "presentations/assets/diagrams"))
    
    (doseq [file (fs/glob root "**/*.org")]
      (when (has-mermaid? file)
        (generate-diagrams file)))
    
    (println "✓ Diagram generation complete")))

(when (= *file* (System/getProperty "babashka.file"))
  (-main))
