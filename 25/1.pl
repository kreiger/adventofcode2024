#!/usr/bin/perl

use warnings;
use strict;

use feature 'say';
use Data::Dumper;

$/ = '';

my @locks = ();
my @keys = ();

while (<>) {
    
    my $lock = !/^\./;
    my @lines = split '\n';
    my @heights = ();
    for my $line (@lines) {
        for (my $x = 0; $x < 5; $x++) {
            if (($lock ? '#' : '.') eq substr $line, $x, 1) {
                $heights[$x]++;
            }
        }
    }
    my $r = { heights => \@heights };
    if ($lock) {
        push @locks, $r;
    } else {
        push @keys, $r;
    }
#    say join ',', @heights; 
#    say;
}

say scalar @locks;
say scalar @keys;

my $total = 0;
for my $lock (@locks) {
    KEY: for my $key (@keys) {
        for (my $x = 0; $x < 5; $x++) {
            next KEY if $key->{heights}->[$x] < $lock->{heights}->[$x];
        }
        $total++;
    } 
}
say $total;
#say Dumper(\@locks);
#say Dumper(\@keys);
