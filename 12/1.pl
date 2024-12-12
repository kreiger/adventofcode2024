#!/usr/bin/perl

use warnings;
use strict;
use integer;
use feature 'say';
use List::Util;
use Data::Dumper;
use lib '../lib';
use Vector2D;

my %plantplots = ();
my %map = ();



my $region = 0;
my $y = 0;
while (<>) {
    chomp;
    my @row = split '';
    my $x = 0;
    my $span = {};
    for my $plant (@row) {
      my $plot = { plant => $plant, pos => vec2d($x, $y), region => $region++ };
      $map{$x}{$y} = $plot;
      push @{$plantplots{$plant}}, $plot;
      $x++;
    }
    $y++;
}

for my $plant (keys %plantplots) {
    my @plots = @{$plantplots{$plant}};

    for my $plot (@plots) {
        my ($x, $y) = ($plot->{x}, $plot->{y});


    }

}