;;; Directory Local Variables for Dutch Clojure Days 2026
;;; For more information see (info "(emacs) Directory Variables")

((org-mode . ((org-confirm-babel-evaluate . nil)
              (org-babel-default-header-args:clojure . 
               ((:tangle . "yes")
                (:mkdirp . "t")
                (:results . "output")
                (:session . "clojure")))
              (org-babel-default-header-args:python . 
               ((:tangle . "yes")
                (:mkdirp . "t")
                (:results . "output")
                (:session . "python")))
              (org-babel-default-header-args:mermaid . 
               ((:file . "presentations/assets/diagrams/diagram.png")
                (:mkdirp . "t")))
              (eval . (add-hook 'after-save-hook 
                               'org-babel-tangle nil t))
              (fill-column . 80)
              (org-export-with-toc . t)
              (org-export-with-author . t)
              (org-html-postamble . nil)))
 (clojure-mode . ((cider-repl-display-help-banner . nil)
                  (cider-preferred-build-tool . deps)
                  (cider-default-cljs-repl . node)))
 (python-mode . ((python-shell-interpreter . "python3")
                 (python-shell-virtualenv-root . ".venv"))))
