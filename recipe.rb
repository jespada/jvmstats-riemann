class Jvmstatsriemann < FPM::Cookery::Recipe
  description 'jvmstats-riemann'

  name 'jvmstats-riemann'
  version '0.1.0'
  revision '0.1'
  homepage 'https://github.com/jespada/jvmstats-riemann'
  source 'git@github.com:jespada/jvmstats-riemann.git', :with => :git, :branch => 'dev-rev'
  arch     'all'

  build_depends 'leiningen'

  def build
    safesystem "lein uberjar"
  end

  def install
    lib('jvmstats-riemann').install Dir["target/jvmstats-riemann-#{version}*standalone.jar"].first, 'jvmstats-riemann.jar'
    bin.install workdir('jvmstats-riemann')
    chmod 0755, bin('jvmstats-riemann')
  end
end
