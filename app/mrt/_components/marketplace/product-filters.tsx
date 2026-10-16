"use client";
import { Card, CardContent } from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";

export function ProductFilters({ search, onSearch }: { search: string; onSearch: (value: string) => void }) {
  return <Card><CardContent className="p-6 space-y-4"><div><Label htmlFor="mr-product-search">शोधा</Label><Input id="mr-product-search" placeholder="उत्पादन किंवा श्रेणी शोधा..." value={search} onChange={e=>onSearch(e.target.value)} /></div><p className="text-sm text-muted-foreground">उत्पादन कार्डवर शेतकऱ्याचा फोन, ईमेल आणि भेटीचे ठिकाण पाहू शकता.</p></CardContent></Card>;
}
