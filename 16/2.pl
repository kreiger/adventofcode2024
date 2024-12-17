#!/usr/bin/perl

use strict;
use warnings;
use feature 'say';
use List::Util 'reduce';

use lib '.';
use Vector2D;
use PriorityQueue;

local $/ = '';

my $map = <>;

my %map = ();

my $start;
my $end;
my $width = 0;
my $y = 0;
for (split '\n', $map) {
  my $x = 0;
  for (split '') {
    $start = V($x, $y) and $_ = '.' if $_ eq 'S';
    $end = V($x, $y) and $_ = '.' if $_ eq 'E';
    $map{$y}{$x} = $_ unless $_ eq '.';
    $x++;
  }
  $width = $x unless $y;
  $y++;
}
my $dim = V($width, $y);;
my $reindeer = { pos => $start, dir => V(1,0), score => 0 } ;


my @paths = a_star([ $reindeer ], $end);

for (@paths) {
  draw(\%map, $_);
  say $_->[-1]->{score}
}


sub draw {
  my $map = shift;
  my $path = shift;
  my %path = map { $_->{pos}, $_->{dir} } @$path ;
  for (my $y = 0; $y < $dim->y; ++$y) {
    for (my $x = 0; $x < $dim->x; ++$x) {
      my $step = $path{V($x,$y)};
      print $step->char and next if $step;
      print $map->{$y}{$x} || '.';
    }
    print "\n";
  }
}

sub empty {
    my $pos = shift;
    return !$map{$pos->y}{$pos->x};
}

my %memo = ();

sub memo_advance {
   my $pos = shift;
   my $dir = shift;
   my $score = shift;
   if (exists $memo{$pos}{$dir}) {
       return $memo{$pos}{$dir};
   }
   my $memo = advance($pos, $dir, $score);
   $memo{$pos}{$dir} = $memo;
   #say "$pos $dir -> $memo->{pos}" if $memo;
   return $memo;
}


sub advance {
    my $pos = shift;
    my $dir = shift;
    my $score = shift;
    $pos+=$dir;
    return undef unless empty($pos);
    my @open = grep { empty($pos+$_) } ($dir->left, $dir->right);
    return {
        pos => $pos,
        dir => $dir,
        score => $score
    } if @open;
    return advance($pos, $dir, $score + 1);
}

my %children = ();

sub children {
  my $path = shift;
  my $node = $path->[-1];
  my $pos = $node->{pos};
  my $dir = $node->{dir};
  my $score = $node->{score};
  
  my @children = 
         map { { pos => $_->{pos},
                 dir => $_->{dir},
                 score => $node->{score} + $_->{score},
         } }
         grep { defined }
         (
           memo_advance($pos,$dir, 1),
           memo_advance($pos,$dir->left, 1001),
           memo_advance($pos,$dir->right, 1001),
         );

  return map { [ @{$path}, $_ ] } @children;
}

sub key {
  my $path = shift;
  my $node = $path->[-1];
  return $node->{pos};
}


sub heuristic {
    my $path = shift;
    my $node = $path->[-1];
    my $pos = $node->{pos};
    my $diff = $end - $pos;
    my $h = $diff->x + $diff->y;
    $h += 1000 if $diff->x and $diff->y;
    return $h;
}

sub a_star {
    my @results = ();
    my ($start, $endkey) = @_;
    my $open = PriorityQueue->new;
    $open->insert($start, 0);
    my %gs = ();
    while (my $node = $open->pop()) {
        push @results, $node and next if key($node) == $endkey;
        for my $child (children($node)) {
            my $childkey = key($child);
            my $tgs = $child->[-1]->{score};
            my $g = $gs{$childkey};
            if (!defined($g) or $tgs <= $g) {
              $gs{$childkey} = $tgs;
              my $h = heuristic($child);
              $open->insert($child, $tgs+$h);
            }
        }
    }
    return @results;
}
