#!/usr/bin/raku

my (%l, %r);

for lines() {
    my ($l, $r) = m/(\d+) \s+ (\d+)/>>.Int;
    %l{$l}++;
    %r{$r} += $r;
}

my $total = 0;

for keys %l -> $l {
    $total += %r{$l} || 0;
}

say $total;
