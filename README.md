# jvmstats-riemann

A Clojure daemon designed to monitor jvm stats through jmx
and output to riemann

## Usage
```
Usage: jvmstats-riemann -h localhost -p 2181 -i 5000 -s riemann.server.com -t "jvm_stats graph metrics"

Options:
  -h, --hostname HOST  localhost/127.0.0.1
  -p, --port PORT                           Port number
  -i, --interval INT                        interval in milliseconds to pull stats
  -s, --server RIEMANNSERVER
  -t, --tags "jvm_stats graph metrics"
```

## Test with Leiningen
```
lein run -- -h localhost -p 2181 -i 5000 -s riemann.server.com -t "jvm_stats graph metrics"
```

##Building

Run ```lein uberjar``` in the project's directory


## Packaging
Install fpm-cookery
``` gem install fpm-cookery```

Clone the repo
```git clone https://github.com/jespada/jvmstats-riemann.git```

Build the .deb
```fpm-cook clean && fpm-cook```

## License

Distributed under the Apache License, Version 2.0.
