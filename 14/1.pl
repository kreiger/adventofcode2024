#!/usr/bin/perl

use warnings;
use strict;
use feature 'say';
use lib '.';
use Vector2D;
use Data::Dumper;
use List::Util qw( product );

my $seconds = 100;

my ($xmax, $ymax) = (0,0);

my @robots = ();
while (<>) {
    chomp;
    my ($px, $py, $vx, $vy) = /p=(\d+),(\d+) v=(-?\d+),(-?\d+)/;
    my $pos = V($px, $py);
    my $vel = V($vx, $vy);
    say "$_ $pos $vel";
    push @robots, { input => $_, pos => $pos, vel => $vel };
    $xmax = $px if $px > $xmax;
    $ymax = $py if $py > $ymax;
}

my $width = $xmax+1;
my $height = $ymax+1;
my $midx = $width >> 1;
my $midy = $height >> 1;
#say Dumper($_) for @robots;

my @quads = ();
for my $robot (@robots) {
    my $pos = $robot->{pos} + ($robot->{vel} * $seconds);
    my $final = V($pos->x % $width, $pos->y % $height);
#    say "$robot->{input} -> $pos -> $final $quad";
    next if $final->x == $midx or $final->y == $midy;
    my $quad = int($final->x > $midx) + 2* int($final->y > $midy);
    say "$robot->{input} -> $pos -> $final $quad";
    $quads[$quad]++;

}
say "$seconds s, dim ".V($width, $height)." mid ".V($midx, $midy);
say "@quads";
say product @quads;