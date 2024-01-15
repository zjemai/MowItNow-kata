#!/bin/sh
. /dnt/applicatifMetier/iam.sh
stop() {
        echo "Stopping iAM..."
        if [ -f $IAM_PID_FILE ]
        then
          pid=$(cat $IAM_PID_FILE)
          /bin/kill -s SIGTERM $pid
          rm $IAM_PID_FILE
        else
          echo "/!\\ Nothing to stop, pid file does not exist '$IAM_PID_FILE'"
        fi
}

start() {
        while true
        do
          stopIAM >/dev/null 2>&1
          $EXEC_IAM &
          pid=$!
          echo $pid > $IAM_PID_FILE && chmod a+rw $IAM_PID_FILE
          echo "[WATCHDOG]: Running iAM with PID $pid saved into '$IAM_PID_FILE'"

          wait $pid
          sig=$?
          echo "Process with PID $pid has finished with Exit status: $sig"

          if [[ "$sig" == "143" ]]
             then
                echo "Stoping IAM with SIGTERM signal"
                exit 1
          else
             echo "[WATCHDOG]: /!\\ Detect iAM is down => Going to restart it!"
          fi

        done
}

case $1 in
    "start")
        startIAM &
        exit 0
        ;;

    "stop")
        stopIAM &
        exit 0
        ;;

    *)
        exit 1
        ;;
esac
