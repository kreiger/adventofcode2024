#!/usr/bin/perl

use warnings;
use strict;
#use Carp::Always;
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
    next if $_ eq '.';
    if ($_ eq '#') {
      $map{$y}{$x} = '#';
      $map{$y}{$x+1} = '#';
      next;
    }
    $map{$y}{$x} = '[';
    $map{$y}{$x+1} = ']';
  } continue {
    $x+=2;
  }
  $width = $x unless $y;
  $y++;
}
my $dim = V($width, $y);

draw(\%map);
my $moves = 0;
for my $dir (@moves) {
    unless (blocked($robot+$dir, $dir)) {
        move($robot+$dir, $dir);
        $robot += $dir;
    }
#    say $dir;
#    draw(\%map);
    say ++$moves;
}

my $total = 0;
for (my $y = 0; $y < $dim->y; ++$y) {
  for (my $x = 0; $x < $dim->x; ++$x) {
    my $what = $map{$y}{$x};
    next unless $what and $what eq '[';
    $total += 100 * $y + $x;
  }
}
say $total;


sub move {
  my $from = shift;
  my $dir = shift;
  my $to = $from + $dir;
  my $here = $map{$from->y}{$from->x} || '';
  return unless $here;
  return if '#' eq $here;
  return move($from-V(1,0), $dir) if $here eq ']';
    if ($dir->y) {
      move($to, $dir);
      move($to+V(1,0), $dir);
    } elsif ($dir->x == 1) {
      move($to+V(1,0), $dir); 
    } else {
      move($to, $dir);
    }
  delete $map{$from->y}{$from->x};
  delete $map{$from->y}{$from->x+1};
  $map{$to->y}{$to->x} = '[';
  $map{$to->y}{$to->x+1} = ']';
}


sub blocked {
  my $from = shift;
  my $dir = shift;
  my $to = $from + $dir;
  my $here = $map{$from->y}{$from->x} || '';
  return blocked($from-V(1,0), $dir) if $here eq ']';
  if ($here eq '[') {
    if ($dir->y) {
      return blocked($to, $dir) || blocked($to+V(1,0), $dir);
    } elsif ($dir->x == 1) {
      return blocked($to+V(1,0), $dir); 
    }
    return blocked($to, $dir);
  }
  return 0 unless $here;
  return 1 if $here eq '#';
  die;
}

sub draw {
  my $map = shift;
  for (my $y = 0; $y < $dim->y; ++$y) {
    for (my $x = 0; $x < $dim->x; ++$x) {
      print '@' and next if $robot == V($x, $y);
      my $what = $map->{$y}{$x};
      print '.' and next unless $what;
      if ('O' eq $what) {
        print '[]';
        $x++;
        next;
      }
      print $what;
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
