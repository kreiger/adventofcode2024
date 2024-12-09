#!/usr/bin/perl

use warnings;
use strict;
use feature 'say';
use Data::Dumper;

local $/;

my $input = <>;

my $id = 0;
my $free = 0;
my @blocks = ();
my @files = ();
my @free = ();
while ($input =~ /(\d)/g) {
    my $len = $1;
    my $id = $free ? '.' : $id++;
    my $start = @blocks;
    my $end = (@blocks + $len);
    my $block = { id => $id,
                  start => $start,
                  len => $len,
                  end => $end };
    push @blocks, $id for 1..$len;
    push @files, $block unless $free;
    push @free, $block if $free;
    $free = !$free;
}

my $start = 0;
while (@files) {
    my $file = pop @files;
    for my $free (@free) {
        last unless $free->{start} < $file->{start};
        next unless $free->{len} >= $file->{len};

        for (my $i = 0; $i < $file->{len}; ++$i) {
            $blocks[$i + $free->{start}] = $file->{id};
            $blocks[$i + $file->{start}] = '.';
        }
        $free->{len} -= $file->{len};
        $free->{start} += $file->{len};
        last;
    }
    shift @free while $free[0]->{len} == 0;
}

my $pos = 0;
my $checksum = 0;
for my $block (@blocks) {
    $checksum += $pos * $block if $block ne '.';
    $pos++;
}

#say map { $_ ne '.' ? $_ % 10 : '.' }  @blocks;

say $checksum;
