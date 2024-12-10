#!/usr/bin/perl

use warnings;
use strict;
use feature 'say';
use Data::Dumper;

my %map = ();
my %positions = ();

my ($width, $height);
while (<>) {
    chomp;
    my $x = 0;
    my $y = $. - 1;
    my @row = split '';
    for my $height (@row) {
        my $h = { x => $x, y => $y, height => $height };
        push @{$positions{$height}}, $h;
        $map{$y}{$x} = $h;
        $x++;
    }
    $width = $x if $y == 0;
    $height = $.;
}

for my $peak (@{$positions{9}}) {
    my ($x, $y) = ($peak->{x}, $peak->{y});
    %{$peak->{peaks}} = ( "$x,$y" => "$x,$y" );
}

for (my $height = 9; $height > 0; $height--) {

    for my $pos (@{$positions{$height}}) {
        next unless $pos->{peaks};
        my ($x, $y) = ($pos->{x}, $pos->{y});
        for my $path (grep { $_ and $_->{height} == $height - 1 } ( $map{$y-1}{$x}, $map{$y}{$x+1}, $map{$y+1}{$x}, $map{$y}{$x-1} )) {
            %{$path->{peaks}} = (%{$pos->{peaks}}, %{$path->{peaks} || {}});
        }
    }
}

my $total = 0;
for my $trailhead (@{$positions{0}}) {
    next unless $trailhead->{peaks};
    $total += keys %{$trailhead->{peaks}};
}

#for (my $height = 9; $height >= 0; $height--) {
#    print Dumper({ $height, $positions{$height} });
#}

#print Dumper(sort { $a->{y} <=> $b->{y} || $a->{x} <=> $b->{x} } $positions{0});
for (my $y = 0; $y < $height; $y++) {
    for (my $x = 0; $x < $width; $x++) {
      print $map{$y}{$x}->{peaks} ? $map{$y}{$x}->{height} : '.';
#      print $map{$y}{$x}{height};
    }
    print "\n";
}

say $total;