# tutorial of Clojure on the web.

### How to run it?

```bash
clojure -M -m webapp.handler
# or
clj
```
```Clojure
user => (require 'webapp.handler)
user => (webapp.handler/-main "3000")
```

Run your webapp during development
Running from the command-line
You can run your webapp any time via clojure -M -m my-webapp.handler as shown above. Once it is running, visit http://localhost:3000 in your browser.

You should be able to stop the webapp by hitting ctrl-c (ctrl-z on Windows).

Note: changes made to your files while the webapp is running from the command-line will not be reflected until you restart the webapp!
Running interactively (in the REPL)
You can also run your webapp interactively, i.e., in the REPL, which allows for changing functions while your webapp is running and seeing those changes immediately.

Add the following comment form after the -main function:
``` clojure
(comment
  ;; evaluate this def form to start the webapp via the REPL:
  ;; :join? false runs the web server in the background!
  (def server (jetty/run-jetty #'app {:port 3000 :join? false}))
  ;; evaluate this form to stop the webapp via the the REPL:
  (.stop server)
  )
```