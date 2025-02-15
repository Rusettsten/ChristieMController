# ChristieMController
An ArtNet based controller for Christie M-Series projectors. Able to send both shutter and power commands.


## Premise
This program is meant to interface with older Christie M Series projectors. These are typically the older lamped-style projectors that were discontinued around 2016. Unfortunately they do not seem to handle ArtNet data reliably, and there is little to no actual information on their fixture profile besides what I found find in an ETC EOS console. The most reliable information I have found is from a [Christie Technical Manual](https://www.christiedigital.com/globalassets/resources/public/020-100224-11-christie-lit-tech-ref-m-series-serial-commands.pdf) on serial commands. There are a number of ways to send data to these projectors. This program sends it via TCP individually to each machine, opening and closing the socket with each request.

This was developed for use on *The Odyssey* at American Repertory Theater in Cambridge, MA. In our scenario, Disguise is broadcasting ArtNet data that the program listens for, and uses to shutter the projector.


## How to use

<details>
<summary>Advanced Users & Developers</summary>
  
  Launch the jarfile as you would any other from a command line. The first time you run it, it will generate a folder next to it where it keeps logs & has a **properties.yml** you can edit to configure your projectors. For more on that, see the [Configuration File](#configuration-file) section.
</details>

<details>
<summary>Beginners & Non-Developers</summary>

  Install the [Java JDK](https://learn.microsoft.com/en-us/java/openjdk/download) for your system. Open Command Prompt (or Terminal on MacOS) and type the following command:
  ```bash
  java -jar C:\path\to\ChristieMController-1.0.2.jar
  ```
  The first time it will generate a folder next to it where it keeps logs & has a **properties.yml** you can edit to configure your projectors. For more on that, see the       [Configuration File](## Configuration File) section.
</details>


## Configuration File
The configuration file is [YAML](https://en.wikipedia.org/wiki/YAML). Do you not need to specify an ethernet adapter, but can if you wish. See [Using A Custom Network Interface](#using-a-custom-network-interface).

In the configuration... 
- you must specify which ArtNet **subnet** and **universe** the program listens on.
- you can add as many projectors as you wish
- the default Christie port is 3002 *and I suggest you leave this setting alone*
- The enabled channel requires it to be above 50% to execute shutter or power commands.
- Leaving any channel as 0 means it is not being used. (meaning if enabled is set to channel 0, it is not required to execute the other commands)

For reference, an example fully filled out configuration is shown below as used on *The Odyssey*.

```yaml
# Configuration for Christie M Controller
# --- ArtNet Configuration ---
networkInterface: "ethernet_32769"
dmxSubnet: 0
dmxUniverse: 1
# --- Projector Configuration ---
projectors:
  - projectorName: "PJ105"
    projectorIp: "10.130.1.35"
    projectorPort: 3002
    enabledChannel: 401
    shutterChannel: 402
    powerChannel: 0
  - projectorName: "PJ106"
    projectorIp: "10.130.1.36"
    projectorPort: 3002
    enabledChannel: 451
    shutterChannel: 452
    powerChannel: 0
```


## Using A Custom Network Interface

As you may have seen in the [Configuration](#configuration-file) section, a custom **networkInferface** is specified. If you are unsure what to put here, just put something random. The next time you launch the program it will provide a list of networkInterfaces and their names that you can use in this part of the configuration.

