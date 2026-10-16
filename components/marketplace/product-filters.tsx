"use client";
import { Card, CardContent } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

export function ProductFilters({ search, onSearch }: { search: string; onSearch: (value: string) => void }) {
  return (
    <Card>
      <CardContent className="p-6 space-y-4">
        <div>
          <Label htmlFor="product-search">Search</Label>
          <Input id="product-search" placeholder="Search products or category..." value={search} onChange={(e) => onSearch(e.target.value)} />
        </div>
        <p className="text-sm text-muted-foreground">
          Search products from the Spring Boot marketplace. Customers can view farmer contact details and location.
        </p>
      </CardContent>
    </Card>
  );
}
