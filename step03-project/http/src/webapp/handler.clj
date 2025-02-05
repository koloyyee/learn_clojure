;; source: https://clojure-doc.org/articles/tutorials/basic_web_development/#conceptual-overview-of-components

(ns webapp.handler
  (:require
   [compojure.core :refer [defroutes GET POST]]
   [compojure.route :as route]
   [ring.adapter.jetty :as jetty]
   [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
   [webapp.views :as views])
  (:gen-class))

(defroutes app-routes ; replace the previous app-routes with this
  (GET "/"
    []
    (views/home-page))
  (GET "/add-location"
    []
    (views/add-location-page))
  (POST "/add-location"
    {params :params}
    (views/add-location-results-page params))
  (GET "/location/:loc-id"
    [loc-id]
    (views/location-page loc-id))
  (GET "/all-locations"
    []
    (views/all-locations-page))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults #'app-routes site-defaults))

(defn -main [& [port]]
  (let [port (parse-long (or port (System/getenv "PORT") 3000))]
    (jetty/run-jetty #'app {:port port})))

;;  (def server (jetty/run-jetty #'app {:port 3000 :join? false}))
;;  (def stop (.stop server))

(comment
    ;; evaluate this def form to start the webapp via the REPL:
  ;; :join? false runs the web server in the background!
  (def server (jetty/run-jetty #'app {:port 3000 :join? false}))
  (.stop server))
