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
            my $anx = $x + $x - $px;
            my $any = $y + $y - $py;
            next if $anx < 0 || $anx >= $width;
            next if $any < 0 || $any >= $height;
            next if $antinodes{$anx}{$any}++;
            $total++;
        }
        push @perm, [$x, $y];
    }

}

say $total;
