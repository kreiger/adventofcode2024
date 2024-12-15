#!/usr/bin/perl

use warnings;
use strict;
use feature 'say';
use lib '.';
use Vector2D;

local $/ = '';

my $map = <>;
my $moves = <>;
my @moves = map { dir($_) }map { split '' } split "\n", $moves;

my %map = ();

my $robot;;
my $width = 0;
my $y = 0;
for (split '\n', $map) {
  my $x = 0;
  for (split '') {
    $robot = V($x, $y) and $_ = '.' if $_ eq '@';
    $map{$y}{$x} = $_ unless $_ eq '.';
    $x++;
  }
  $width = $x unless $y;
  $y++;
}
my $dim = V($width, $y);

#draw(\%map);

for my $dir (@moves) {
    $robot += $dir if move($robot, $dir);

    #draw(\%map);
}

my $total = 0;
for (my $y = 0; $y < $dim->y; ++$y) {
  for (my $x = 0; $x < $dim->x; ++$x) {
    my $what = $map{$y}{$x};
    next unless $what and $what eq 'O';
    $total += 100 * $y + $x;
  }
}
say $total;


sub move {
  my $from = shift;
  my $dir = shift;
  my $to = $from + $dir;
  my $something_there = $map{$to->y}{$to->x};
  return 0 if $something_there and $something_there eq '#';
  if (!$something_there || move($to, $dir)) {
    $map{$to->y}{$to->x} = $map{$from->y}{$from->x};
    delete $map{$from->y}{$from->x};
    return 1;
  }
  return 0;
}

sub draw {
  my $map = shift;
  for (my $y = 0; $y < $dim->y; ++$y) {
    for (my $x = 0; $x < $dim->x; ++$x) {
      print '@' and next if $robot == V($x, $y);
      print $map->{$y}{$x} || '.';
    }
    print "\n";
  }
}

sub for_all {
  my $map = shift;
  my $f = shift;
  for (my $y = 0; $y < $dim->y; ++$y) {
    for (my $x = 0; $x < $dim->x; ++$x) {
      $f->($map->{$y}{$x}, V($x,$y));
    }
  }
}

sub dir {
    my $dir = shift;
    return V( 0,-1) if $dir eq '^';
    return V( 1, 0) if $dir eq '>';
    return V( 0, 1) if $dir eq 'v';
    return V(-1, 0) if $dir eq '<';
}
