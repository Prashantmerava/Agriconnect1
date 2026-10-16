"use client";

import { Card, CardContent, CardFooter, CardHeader } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import Image from "next/image";
import { useEffect, useState } from "react";
import { api } from "@/lib/api";
import type { Product } from "@/types";
import { Phone, Mail, MapPin, UserRound } from "lucide-react";

export function ProductGrid({ search }: { search: string }) {
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  useEffect(() => {
    const timer = setTimeout(async () => {
      setLoading(true); setError("");
      try { const r = await api.get("/api/products", { params: search.trim() ? { search: search.trim() } : undefined }); setProducts(r.data.products || []); }
      catch (e:any) { setError(e.response?.data?.msg || "उत्पादने लोड करता आली नाहीत. Spring Boot backend सुरू आहे का ते तपासा."); }
      finally { setLoading(false); }
    }, 300);
    return () => clearTimeout(timer);
  }, [search]);
  if (loading) return <p className="text-muted-foreground">उत्पादने लोड होत आहेत...</p>;
  if (error) return <p className="text-red-500">{error}</p>;
  if (!products.length) return <p className="text-muted-foreground">जुळणारे उत्पादन सापडले नाही.</p>;
  return <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
    {products.map(product => <Card key={product.id} className="overflow-hidden">
      <CardHeader className="p-0"><div className="relative h-48 bg-muted">{product.imageUrl ? <Image src={product.imageUrl} alt={product.name} fill className="object-cover" unoptimized /> : <div className="h-full flex items-center justify-center text-muted-foreground">प्रतिमा नाही</div>}</div></CardHeader>
      <CardContent className="p-4 space-y-2">
        <h3 className="font-semibold text-lg">{product.name}</h3><p className="text-sm text-muted-foreground">{product.description}</p>
        <p className="text-lg font-bold">₹{product.price}/{product.unit}</p><p className="text-sm">उपलब्ध: {product.quantity} {product.unit}</p><p className="text-xs text-muted-foreground">अंदाजित किंमत: ₹{product.predictedPrice}/{product.unit}</p>
        <div className="rounded-lg border p-3 space-y-1 text-sm"><p className="font-semibold flex items-center gap-2"><UserRound className="h-4 w-4" />{product.farmer.name}</p><p className="flex items-center gap-2"><Phone className="h-4 w-4" />{product.farmer.phoneNumber}</p><p className="flex items-center gap-2"><Mail className="h-4 w-4" />{product.farmer.email}</p><p className="flex items-center gap-2"><MapPin className="h-4 w-4" />{product.location?.address || product.farmer.address}</p></div>
      </CardContent>
      <CardFooter className="p-4 pt-0 grid grid-cols-2 gap-2"><Button asChild><a href={`tel:${product.farmer.phoneNumber}`}><Phone className="h-4 w-4 mr-2" />कॉल करा</a></Button><Button variant="outline" asChild><a href={`mailto:${product.farmer.email}`}><Mail className="h-4 w-4 mr-2" />ईमेल</a></Button></CardFooter>
    </Card>)}
  </div>;
}
