# Welcome to RYA #

Rya is a cryptocurrency and monetary system designed to be both a store of value and a medium of exchange. Using a free, decentralized monetary market, the Rya system tethers money supply to credit without the need for a monopolistic regulatory institution. Money supply is adjusted dynamically depending on economic cycles making for a better medium of exchange. Rya and its Proof of Trust (PoT) model is therefore expected to be a fuller and more complete substitute for Fiat monetary systems than any cryptocurrency yet.

Participance with the Rya blockchain unables: 

Lend and Barrow money
View accounts calculated credit score
Calculate local and global interest rates


Read more about Rya vision here: https://ryacoin.io/WP/RyoWhitepaper.pdf

This Rya code is a fork from NXT project: https://bitbucket.org/Jelurida/nxt-clone-starter

Jelurida lisence is attached.

----
## Get it! ##

  - *pre-packaged* - `coming soon`

  - *dependencies*:
    - *general* - Java 8
    - *Ubuntu* - `http://www.webupd8.org/2012/09/install-oracle-java-8-in-ubuntu-via-ppa.html`
    - *Debian* - `http://www.webupd8.org/2014/03/how-to-install-oracle-java-8-in-debian.html`
    - *FreeBSD* - `pkg install openjdk8`

  - *repository* - `git clone `
  
----
## Run it! ##

  - click on the Rya icon, or start from the command line:
  - Unix: `./start.sh`
  - Mac: `./run.command`
  - Window: `run.bat`

  - wait for the JavaFX wallet window to open
  - on platforms without JavaFX, open http://localhost:7876/ in a browser

----
## Compile it! ##

  - if necessary with: `./compile.sh`
  - you need jdk-8 as well

----
## Improve it! ##

  - we also love **pull requests**
  - we also love issues (resolved ones actually ;-) )
  - in any case, make sure you leave **your ideas** at BitBucket
  - assist others on the issue tracker
  - **review** existing code and pull requests
  - cf. coding guidelines in DEVELOPERS-GUIDE.md

...
on the forums:

...

----
## Testnet ##

The Testnet is a copy of our system where you can try out every functionality.
Our Test is installed here machine located in https://console.cloud.google.com/compute/instances?project=gta2-186320
Today this is the external IP: 35.190.163.228 and you can SSH this machine. DO NOT RESTART THIS MACHINE!!! (IP address will be changed as a result of it)
The only testnet peer you will have in your wallet from now on is the above testnet machine

  - What should I do to use it?
    - You must pull all the changes after 20.12.17
  - Which ports are used by the testnet?
    - 8877(SSL port), 8876 (non-SSL port / API server port), 8875(UI port), 8874(peer port
  - How can I upgrade the testnet node with my new changes
    - Prepare your new changes:
        - Compile your changes
        - Zip the whole directory you cloned after you compile it successfully.
    - Connect the testnet vm:
        - Go to https://console.cloud.google.com/compute/instances?project=gta2-186320
        - Click on the connect "SSH" button
    - Stop the current testnet running node
        - Run: "ps -fC java" , it will print out the java process id (pid)
        - Kill the java process but run: "kill -9 <java pid>"
        - Rename the current "rya" directoy so if something will go wrong you will still have a restore point to go back to.
    - Start the new testnet node
        - Transfer the zip file into the root directory of the linux cmd that was just opened
        - Unzip the zip file into a new "rya" directory
        - Run: cd ~/rya
        - Run: nohup ./run.sh &
        - Run: exit
    - Validate your node is functioning well:
        - Once you run the run.sh command you will be notify that a log file was opened in the name of nohup.out, make sure you have no exceptions there and it includes the line "Rya server <version> started successfully."
        - Notify the team you have upgraded the testnet node.
  -  What shell I do if i had to restart the testnet VM:
      - Change the deafultTestnetPeers value in <testnet VM root>\conf\rya-default.properties to have the new external IP and restart the node (section 3 and then 4 with the same node).
      - Commit the same change into our repository.
      - Let everyone know you have done it and they must get your latest commit and rerun their node to use the testnet peer that have new IP now.
----
