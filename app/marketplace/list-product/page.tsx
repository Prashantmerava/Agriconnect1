"use client";
import { useState } from "react";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { useToast } from "@/hooks/use-toast";
import { api } from "@/lib/api";
import { Loader2 } from "lucide-react";

export default function ListProductPage() {
  const router = useRouter(); const {toast}=useToast(); const [loading,setLoading]=useState(false); const [imageLoading,setImageLoading]=useState(false);
  const [form,setForm]=useState({productName:"",description:"",category:"",price:"",quantity:"",unit:"",harvestDate:"",imageUrl:"",locationAddress:"",latitude:"",longitude:""});
  const set=(key:string,value:string)=>setForm(p=>({...p,[key]:value}));
  const upload=async(file:File)=>{setImageLoading(true);try{const data=new FormData();data.append("file",file);const res=await api.post("/api/images/upload",data,{headers:{"Content-Type":"multipart/form-data"}});set("imageUrl",res.data.url);toast({title:"Image uploaded"});}catch(e:any){toast({title:e.response?.data?.msg||"Image upload failed",variant:"destructive"});}finally{setImageLoading(false)}};
  const submit=async(e:React.FormEvent)=>{e.preventDefault();setLoading(true);try{await api.post("/api/marketplace",{productName:form.productName,description:form.description,category:form.category,price:Number(form.price),quantity:Number(form.quantity),unit:form.unit,harvestDate:form.harvestDate,imageUrl:form.imageUrl,locationAddress:form.locationAddress,latitude:Number(form.latitude),longitude:Number(form.longitude)});toast({title:"Success",description:"New product added successfully"});router.push("/marketplace");}catch(e:any){toast({title:e.response?.data?.msg||"Unable to add product",variant:"destructive"});}finally{setLoading(false)}};
  return <div className="container mx-auto py-10"><Card className="w-full max-w-2xl mx-auto"><CardHeader><CardTitle>List a New Product</CardTitle><CardDescription>Add your product and meeting location to the marketplace.</CardDescription></CardHeader><CardContent><form onSubmit={submit} className="space-y-5">
    <Input required minLength={2} placeholder="Product name" value={form.productName} onChange={e=>set("productName",e.target.value)}/>
    <Textarea required minLength={10} placeholder="Description" value={form.description} onChange={e=>set("description",e.target.value)}/>
    <Select value={form.category} onValueChange={v=>set("category",v)}><SelectTrigger><SelectValue placeholder="Select category"/></SelectTrigger><SelectContent><SelectItem value="fruits">Fruits</SelectItem><SelectItem value="vegetables">Vegetables</SelectItem><SelectItem value="grains">Grains</SelectItem><SelectItem value="dairy">Dairy</SelectItem><SelectItem value="other">Other</SelectItem></SelectContent></Select>
    <div className="grid grid-cols-3 gap-3"><Input required type="number" min="0.01" step="0.01" placeholder="Price ₹" value={form.price} onChange={e=>set("price",e.target.value)}/><Input required type="number" min="1" placeholder="Quantity" value={form.quantity} onChange={e=>set("quantity",e.target.value)}/><Select value={form.unit} onValueChange={v=>set("unit",v)}><SelectTrigger><SelectValue placeholder="Unit"/></SelectTrigger><SelectContent><SelectItem value="kg">kg</SelectItem><SelectItem value="g">g</SelectItem><SelectItem value="l">L</SelectItem><SelectItem value="piece">Piece</SelectItem><SelectItem value="dozen">Dozen</SelectItem></SelectContent></Select></div>
    <Input required type="date" value={form.harvestDate} onChange={e=>set("harvestDate",e.target.value)}/>
    <div><label className="text-sm font-medium">Product image</label><Input type="file" accept="image/*" className="mt-2" disabled={imageLoading} onChange={e=>{const f=e.target.files?.[0];if(f)upload(f)}}/>{form.imageUrl&&<p className="text-xs text-green-600 mt-1">Image uploaded successfully.</p>}</div>
    <div className="rounded-lg border p-4 space-y-3"><h3 className="font-semibold">Farmer meeting / product location</h3><Input required placeholder="Address (e.g. Shirol, Kolhapur)" value={form.locationAddress} onChange={e=>set("locationAddress",e.target.value)}/><div className="grid grid-cols-2 gap-3"><Input required type="number" step="any" placeholder="Latitude" value={form.latitude} onChange={e=>set("latitude",e.target.value)}/><Input required type="number" step="any" placeholder="Longitude" value={form.longitude} onChange={e=>set("longitude",e.target.value)}/></div><p className="text-xs text-muted-foreground">Customers will see this location on the map.</p></div>
    <Button type="submit" className="w-full" disabled={loading||imageLoading}>{loading?<><Loader2 className="mr-2 h-4 w-4 animate-spin"/>Listing...</>:"List Product"}</Button>
  </form></CardContent></Card></div>;
}
