#!/usr/bin/perl

use warnings;
use strict;
use Carp;
use Data::Dumper;

use feature 'say';
#use feature 'bitwise';

my %nodes = ();

my %ops = ();
my %alias = ();
while (<>) {
    if (/^(\w+) (\w+) (\w+) -> (\w+)/) {
        my ($l, $op, $r, $node) = ($1, $2, $3, $4);
        my @ns = sort ($l, $r);
        $nodes{$node} = {
            l => $ns[0],
            op => $op,
            r => $ns[1],
            orig => $node,
            s => "$ns[0] $op $ns[1] -> $node"
        }
    }

}

for my $node (values %nodes) {
    $node->{lnode} = $nodes{$node->{l}};
    $node->{rnode} = $nodes{$node->{r}};
}

my @nodes = sort { $a->{l} cmp $b->{l} || $a->{r} cmp $b->{r} || $a->{op} cmp $b->{op} } values %nodes;
for my $n (@nodes) {    
    if ($n->{orig} =~ /z\d\d/) {
        if ($n->{op} eq 'XOR') {
        } else {
            say "($n->{lnode}->{s}) $n->{op} ($n->{rnode}->{s}) -> $n->{orig}";
        }
    } elsif ($n->{op} eq 'XOR') {
        if ($n->{orig} =~ /z\d\d/) {
        } elsif ($n->{l} =~ /[xy]\d\d/ and $n->{r} =~ /[xy]\d\d/) {
        } else {
            say "($n->{lnode}->{s}) $n->{op} ($n->{rnode}->{s}) -> $n->{orig}";
        }
    } elsif ($n->{op} eq 'AND') {
        if ($n->{l} =~ /[xy]\d\d/ and $n->{r} =~ /[xy]\d\d/) {
        } elsif ($n->{lnode}->{op} eq 'XOR' and $n->{rnode}->{op} eq 'OR') {
        } elsif ($n->{rnode}->{op} eq 'XOR' and $n->{lnode}->{op} eq 'OR') {
        } else {
            say "($n->{lnode}->{s}) $n->{op} ($n->{rnode}->{s}) -> $n->{orig}";
        }
    } elsif ($n->{op} eq 'OR') {
        if ($n->{lnode}->{op} eq 'AND' and $n->{rnode}->{op} eq 'AND') {
        } else {
            say "($n->{lnode}->{s}) $n->{op} ($n->{rnode}->{s}) -> $n->{orig}";
        }
    }
}



#for my $node (values %nodes) {
    #visit($node);
#}

sub visit {
    my $node = shift;
    my $l = $node->{l};
    my $op = $node->{op};
    my $r = $node->{r};
    my $lnode = $nodes{$l};
    my $rnode = $nodes{$r};
    unless ($lnode and $rnode) {
        $node->{alias} = "$l\_$op\_$r";
        return;
    }
    if ($lnode) {
        visit($lnode);
        $l = $lnode->{alias};
    }
    if ($rnode) {
        visit($rnode);
        $r = $rnode->{alias};
    }
}
