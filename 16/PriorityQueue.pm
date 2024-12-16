package PriorityQueue;
our $VERSION = '0.01';
use strict;
use warnings;
sub new {
        return bless {
                queue => [],
                prios => {},     # by payload
        }, shift();
}
sub pop {
        my ($self) = @_;
        if (@{$self->{queue}} == 0) {
                return undef;
        }
        delete($self->{prios}->{$self->{queue}->[0]});
        return shift(@{$self->{queue}});
}
sub unchecked_insert {
        my ($self, $payload, $priority, $lower, $upper) = @_;
        $lower = 0                             unless defined($lower);
        $upper = scalar(@{$self->{queue}}) - 1 unless defined($upper);
        # first of all, map the payload to the desired priority
        # run an update if the element already exists
        $self->{prios}->{$payload} = $priority;
        # And register the payload in the queue. There are a lot of special
        # cases that can be exploited to save us from doing the relatively
        # expensive binary search.
        # Special case: No items in the queue.  The queue IS the item.
        if (@{$self->{queue}} == 0) {
                push(@{$self->{queue}}, $payload);
                return;
        }
        # Special case: The new item belongs at the end of the queue.
        if ($priority >= $self->{prios}->{$self->{queue}->[-1]}) {
                push(@{$self->{queue}}, $payload);
                return;
        }
        # Special case: The new item belongs at the head of the queue.
        if ($priority < $self->{prios}->{$self->{queue}->[0]}) {
                unshift(@{$self->{queue}}, $payload);
                return;
        }
        # Special case: There are only two items in the queue.  This item
        # naturally belongs between them (as we have excluded the other
        # possible positions before)
        if (@{$self->{queue}} == 2) {
                splice(@{$self->{queue}}, 1, 0, $payload);
                return;
        }
        # And finally we have a nontrivial queue.  Insert the item using a
        # binary seek.
        # Do this until the upper and lower bounds crossed... in which case we
        # will insert at the lower point
        my $midpoint;
        while ($upper >= $lower) {
                $midpoint = ($upper + $lower) >> 1;
                # We're looking for a priority lower than the one at the midpoint.
                # Set the new upper point to just before the midpoint.
                if ($priority < $self->{prios}->{$self->{queue}->[$midpoint]}) {
                        $upper = $midpoint - 1;
                        next;
                }
                # We're looking for a priority greater or equal to the one at the
                # midpoint.  The new lower bound is just after the midpoint.
                $lower = $midpoint + 1;
        }
        splice(@{$self->{queue}}, $lower, 0, $payload);
}
sub _find_payload_pos {
        my ($self, $payload) = @_;
        my $priority = $self->{prios}->{$payload};
        if (!defined($priority)) {
                return undef;
        }
        # Find the item with binary search.
        # Do this until the bounds are crossed, in which case the lower point
        # is aimed at an element with a higher priority than the target
        my $lower = 0;
        my $upper = @{$self->{queue}} - 1;
        my $midpoint;
        while ($upper >= $lower) {
                $midpoint = ($upper + $lower) >> 1;
                # We're looking for a priority lower than the one at the midpoint.
                # Set the new upper point to just before the midpoint.
                if ($priority < $self->{prios}->{$self->{queue}->[$midpoint]}) {
                        $upper = $midpoint - 1;
                        next;
                }
                # We're looking for a priority greater or equal to the one at the
                # midpoint.  The new lower bound is just after the midpoint.
                $lower = $midpoint + 1;
        }
        # The lower index is now pointing to an element with a priority higher
        # than our target.  Scan backwards until we find the target.
        while ($lower-- >= 0) {
                return $lower if ($self->{queue}->[$lower] eq $payload);
        }
}
sub delete {
        my ($self, $payload) = @_;
        my $pos = $self->_find_payload_pos($payload);
        if (!defined($pos)) {
                return undef;
        }
        delete($self->{prios}->{$payload});
        splice(@{$self->{queue}}, $pos, 1);
        return $pos;
}
sub unchecked_update {
        my ($self, $payload, $new_prio) = @_;
        my $old_prio = $self->{prios}->{$payload};
        # delete the old item
        my $old_pos = $self->delete($payload);
        # reinsert the item, limiting the range for the binary search (if needed)
        # a bit by checking how the priority changed.
        my ($upper, $lower);
        if ($new_prio - $old_prio > 0) {
                $upper = @{$self->{queue}};
                $lower = $old_pos;
        } else {
                $upper = $old_pos;
                $lower = 0;
        }
        $self->unchecked_insert($payload, $new_prio, $lower, $upper);
}
sub update {
        my ($self, $payload, $prio) = @_;
        if (!defined($self->{prios}->{$payload})) {
                goto &unchecked_insert;
        } else {
                goto &unchecked_update;
        }
}
*insert = \&update;
1;
__END__
