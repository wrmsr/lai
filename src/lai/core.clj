(ns lai.core
  (:require [taoensso.timbre :as timbre])
  (:import

   com.googlecode.lanterna.TerminalPosition
   com.googlecode.lanterna.TerminalSize
   com.googlecode.lanterna.TextColor
   com.googlecode.lanterna.TextColor$RGB
   com.googlecode.lanterna.graphics.TextGraphics
   com.googlecode.lanterna.screen.DefaultScreen
   com.googlecode.lanterna.screen.Screen
   com.googlecode.lanterna.screen.Screen$RefreshType
   com.googlecode.lanterna.terminal.DefaultTerminalFactory
   com.googlecode.lanterna.terminal.Terminal
   com.googlecode.lanterna.terminal.TerminalFactory
   java.nio.charset.Charset

   )
  (:gen-class))

(timbre/refer-timbre)

(defn- print-terminal-string [^Terminal terminal ^String string]
  (doseq [i (range (.length string))]
    (.putCharacter terminal (.charAt string i))))

(defn nrepl [port]
  (require 'clojure.tools.nrepl.server)
  (with-out-str
    ((resolve 'clojure.tools.nrepl.server/start-server) :port port))
  (info "nREPL server listening on" port))

(defrecord Buffer [^String contents])
(defrecord Window [^int x ^int y ^int width ^int height buffer])
(defrecord Frame [])

(defn draw-window [^Window window ^TextGraphics text-graphics]
  (.setForegroundColor text-graphics (new TextColor$RGB 0 0 0))
  (.setBackgroundColor text-graphics (new TextColor$RGB 0 0 0))
  (.setPosition text-graphics (new TerminalPosition (inc (:x window)) (inc (:y window))))
  (.fillRectangle text-graphics (new TerminalSize (- (:width window) 2) (- (:height window) 2)) \space)

  (.setForegroundColor text-graphics (new TextColor$RGB 0 0 0))
  (.setBackgroundColor text-graphics (new TextColor$RGB 255 255 255))
  (.setPosition text-graphics (new TerminalPosition (:x window) (:y window)))
  (.drawRectangle text-graphics (new TerminalSize (:width window) (:height window)) \|)

  (when-let [buffer (:buffer window)]
    (.setForegroundColor text-graphics (new TextColor$RGB 255 255 255))
    (.setBackgroundColor text-graphics (new TextColor$RGB 0 0 0))
    (.setPosition text-graphics (new TerminalPosition (inc (:x window)) (inc (:y window))))
    (.putString text-graphics (:contents buffer))))

(defn -main [& args]
  (nrepl 7888)
  (let [^TerminalFactory terminal-factory (new DefaultTerminalFactory)
        ^Terminal terminal (.createTerminal terminal-factory)
        ^TerminalSize terminal-size (.getTerminalSize terminal)
        ^Screen screen (new DefaultScreen terminal)
        ^TextGraphics text-graphics (.newTextGraphics screen)]
    (.startScreen screen)
    (try
      (do
        (draw-window (Window. 10 10 100 40 (Buffer. "hi there")) text-graphics)
        (.refresh screen Screen$RefreshType/DELTA)
        (Thread/sleep 1000))
      (finally
       (.stopScreen screen))))
  (shutdown-agents)
  (System/exit 0))
