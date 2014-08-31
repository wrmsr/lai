(ns lai.core
  (:import

   com.googlecode.lanterna.gui2.DefaultWindow
   com.googlecode.lanterna.gui2.DefaultWindowTextGUI
   com.googlecode.lanterna.gui2.Window
   com.googlecode.lanterna.gui2.WindowBasedTextGUI
   com.googlecode.lanterna.gui2.WindowManager
   com.googlecode.lanterna.gui2.WindowManager$Hint
   com.googlecode.lanterna.screen.DefaultScreen
   com.googlecode.lanterna.screen.Screen
   com.googlecode.lanterna.terminal.DefaultTerminalFactory
   com.googlecode.lanterna.terminal.Terminal
   java.nio.charset.Charset

   )
  (:gen-class))

(defn -main
  [& args]
  (let [terminal-factory (new DefaultTerminalFactory)
        ^Terminal terminal (.createTerminal terminal-factory)
        ^Screen screen (new DefaultScreen terminal)
        ^WindowBasedTextGUI text-gui (new DefaultWindowTextGUI screen)
        ^Window window (new DefaultWindow "Dialog Test")
        ^WindowManager window-manager (.getWindowManager text-gui)]
    (.addWindow window-manager window (into-array WindowManager$Hint nil))
    (.startScreen screen)
    (.updateScreen text-gui)
    (try
      (loop []
        (when (not (.isEmpty (.getWindows window-manager)))
          (.processInput text-gui)
          (if (.isPendingUpdate text-gui)
            (.updateScreen text-gui)
            (Thread/sleep 1))
          (recur)))
      (finally
       (.stopScreen screen)))))
