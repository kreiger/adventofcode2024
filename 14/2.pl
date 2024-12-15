#!/usr/bin/perl

use warnings;
use strict;
use feature 'say';
use lib '.';
use Vector2D;
use Data::Dumper;
use List::Util qw( product );

my ($xmax, $ymax) = (0,0);

my @robots = ();
while (<>) {
    chomp;
    my ($px, $py, $vx, $vy) = /p=(\d+),(\d+) v=(-?\d+),(-?\d+)/;
    my $pos = V($px, $py);
    my $vel = V($vx, $vy);
    push @robots, { input => $_, pos => $pos, vel => $vel };
    $xmax = $px if $px > $xmax;
    $ymax = $py if $py > $ymax;
}

my $width = $xmax+1;
my $height = $ymax+1;
my $dim = V($width,$height);
my $midx = $width >> 1;
my $midy = $height >> 1;
#say Dumper($_) for @robots;

my $seconds = 0;
my $maxcount = 0;
while (1) {
  $seconds++;
  my %map=();
  for my $robot (@robots) {
    $robot->{pos} = ($robot->{pos} + $robot->{vel}) % $dim;
    my $pos = $robot->{pos};
    $map{$pos->y}{$pos->x} = '#';
  }
  if ($seconds) {
    my $maxc = draw(\%map, $seconds);
    draw(\%map, $seconds, 1) and exit if $maxc > 7;
  }
}

sub draw {
  my $consecutive = 0;
  my $maxc = 0;
  my $map = shift;
  my $seconds = shift;
  my $draw = shift;
    for (my $y = 0; $y < $height; $y++) {
      for (my $x = 0; $x < $width; $x++) {
        if ($map->{$y}{$x}) {
          print '#' if $draw;
          $consecutive++;
          $maxc = $consecutive if $consecutive > $maxc;
          next;
        };
        $consecutive = 0;
        print '.' if $draw;
      }
      print "\n" if $draw;
    } 
    say "$seconds";# if $seconds;
    return $maxc;
}

