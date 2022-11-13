# Minimal TPA

A lightweight TPA plugin for SpigotMC and Paper servers

---

### Commands

* `/tpa|tpask <player>` - send a teleport request to `player`
* `/tpaccept` - accept your current incoming teleport request
* `/tpdeny` - deny your current incoming teleport request
* `/tpcancel` - cancel your current outgoing teleport request
* `/back` - return to your previous location, where you were before teleporting to someone or dying

---

### Permissions

* `minimaltpa.*` - wildcard permission for operators
* `minimaltpa.tpa` - send TPA requests; defaults to `true`
* `minimaltpa.tpaccept` - accept TPA requests; defaults to `true`
* `minimaltpa.tpdeny` - deny TPA requests; defaults to `true`
* `minimaltpa.tpcancel` - cancel TPA requests; defaults to `true`
* `minimaltpa.back` - return to your location before teleporting to someone or dying; defaults to `true`
* `minimaltpa.bypasscooldown` - bypass the cooldown between TPA requests; defaults to `false` for non-operators

---

### Configuration Options

* `keep-alive` - how long to keep a TPA request alive for until it times out
* `cooldown` - how long players must wait between sending TPA requests