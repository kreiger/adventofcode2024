#!/usr/bin/perl

use warnings;
use strict;
use feature 'say';
use Data::Dumper;

my %nodes = ();
my ($width, $height);

while (<>) {
    chomp;
    my ($x, $y) = (0, $. - 1);
    for (split '') {
        push @{$nodes{$_}}, [$x,$y] unless $_ eq '.';
        $x++;
    }
    $width = $x unless $y;
}
$height = $.;

my %antinodes = ();

my $total = 0;

for my $freq (keys %nodes) {
    my @pos = @{$nodes{$freq}};
    my @perm = @pos;
    for (@pos) {
        my ($x, $y) = @{shift @perm};
        for my $p (@perm) {
            my ($px, $py) = @{$p};
            my ($dx, $dy) = ($x - $px, $y - $py);
            my ($anx, $any) = ($x, $y);
            while ($anx >= 0 && $anx < $width && $any >= 0 && $any < $height) {
                $total++ unless $antinodes{$anx}{$any}++;
                ($anx, $any) = ($anx+$dx,$any+$dy)
            }
        }
        push @perm, [$x, $y];
    }
}

for (my $y = 0; $y < $height; $y++) {
  for (my $x = 0; $x < $width; $x++) {
    my $c = $antinodes{$x}{$y} ? '#' : '.';
    print $c;
  }
  print "\n";
}


say $total;
