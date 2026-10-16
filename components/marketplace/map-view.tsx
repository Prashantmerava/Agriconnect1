"use client";
import { Card } from "@/components/ui/card";
import { MapContainer, TileLayer, Popup, Marker } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import { Icon } from "leaflet";
import { useEffect, useState } from "react";
import type { LocationData } from "@/types";
import { api } from "@/lib/api";

export function MapView() {
  const [mounted, setMounted] = useState(false); const [locations, setLocations] = useState<LocationData[]>([]);
  useEffect(() => { setMounted(true); api.get("/api/locations").then(r => setLocations(r.data.data || [])).catch(console.error); }, []);
  if (!mounted) return null;
  return <Card className="mt-6"><div className="h-[600px] w-full"><MapContainer center={[18.5204,73.8567]} zoom={7} style={{height:"100%",width:"100%"}}><TileLayer attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors' url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />{locations.map(l => <Marker key={l.id} position={[l.latitude,l.longitude]} icon={new Icon({iconUrl:"/marker.png",iconSize:[35,41],iconAnchor:[12,41]})}><Popup><div className="p-2"><h3 className="font-semibold">{l.address}</h3><p className="text-sm">Product: {l.productName}</p><p className="text-sm">Price: ₹{l.price}</p></div></Popup></Marker>)}</MapContainer></div></Card>;
}
