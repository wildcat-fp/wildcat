# To learn more about how to use Nix to configure your environment
# see: https://developers.google.com/idx/guides/customize-idx-env
{ pkgs, ... }: {
  # Which nixpkgs channel to use.
  channel = "stable-25.05"; # or "unstable"

  # Use https://search.nixos.org/packages to find packages
  packages = [
    pkgs.python3

    pkgs.zulu
    pkgs.zsh

    pkgs.bat
    pkgs.tree
    pkgs.gh
    pkgs.gnupg
    pkgs.pinentry-curses

    pkgs.chezmoi
    pkgs.util-linux
  ];

  # Sets environment variables in the workspace
  env = {
    ZSH = "${pkgs.oh-my-zsh}/share/oh-my-zsh";
  };

  idx = {
    # Search for the extensions you want on https://open-vsx.org/ and use "publisher.id"
    extensions = [
      # "vscodevim.vim"
      "redhat.java"
      "github.vscode-github-actions"
      "github.vscode-pull-request-github"
      "EditorConfig.EditorConfig"
      "fwcd.kotlin"
      "KnisterPeter.vscode-github"
      "vscjava.vscode-gradle"
      "vscjava.vscode-java-debug"
      "vscjava.vscode-java-dependency"
      "vscjava.vscode-java-pack"
      "vscjava.vscode-java-test"
      "vscjava.vscode-maven"
      "redhat.vscode-yaml"
      "zjffun.snippetsmanager"
    ];

  };
}
