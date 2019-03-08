Tutorial on building REST services with Spring using HATEOAS.

This tutorial engages in various tactics to build REST API.

The following tactics help make your services less likely to break existing clients you may or may not control:

-Don’t remove old fields. Instead, support them.

-Use rel-based links so clients don’t have to hard code URIs.

-Retain old links as long as possible. Even if you have to change the URI, keep the rels so older clients have a path onto the newer features.

-Use links, not payload data, to instruct clients when various state-driving operations are available.