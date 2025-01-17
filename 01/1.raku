#!/usr/bin/raku

my (@l, @r);

for lines() {
    my ($l, $r) = m/(\d+) \s+ (\d+)/>>.Int;
    push @l, $l;
    push @r, $r;
}

@l = sort @l;
@r = sort @r;


my $total = 0;

for zip @l, @r -> [$l, $r] {
    $total += abs($l - $r);
}

say $total;
