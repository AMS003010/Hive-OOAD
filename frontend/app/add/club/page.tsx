'use client';

import { useState } from 'react';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { Textarea } from '@/components/ui/textarea';
import { Card, CardContent } from '@/components/ui/card';
import { Label } from '@/components/ui/label';

export default function AddClubForm() {
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    club_chair: '',
    club_coordinator: '',
    email: '',
    mem_count: '',
    category: '',
  });

  const [status, setStatus] = useState<'idle' | 'success' | 'error'>('idle');

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8000/api/club', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData),
      });
      setStatus(response.ok ? 'success' : 'error');
    } catch {
      setStatus('error');
    }
  };

  return (
    <Card className="max-w-2xl mx-auto mt-10 shadow-md">
      <CardContent className="p-6 space-y-4">
        <h2 className="text-2xl font-bold mb-4">Add a New Club</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <Label htmlFor="name">Club Name</Label>
            <Input name="name" value={formData.name} onChange={handleChange} required />
          </div>

          <div>
            <Label htmlFor="description">Description</Label>
            <Textarea name="description" value={formData.description} onChange={handleChange} required />
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <Label htmlFor="club_chair">Club Chair</Label>
              <Input name="club_chair" value={formData.club_chair} onChange={handleChange} required />
            </div>
            <div>
              <Label htmlFor="club_coordinator">Club Coordinator</Label>
              <Input name="club_coordinator" value={formData.club_coordinator} onChange={handleChange} required />
            </div>
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <Label htmlFor="email">Email</Label>
              <Input type="email" name="email" value={formData.email} onChange={handleChange} required />
            </div>
            <div>
              <Label htmlFor="mem_count">Member Count</Label>
              <Input type="number" name="mem_count" value={formData.mem_count} onChange={handleChange} required />
            </div>
          </div>

            <div>
                <Label htmlFor="category">Category</Label>
                <select
                    name="category"
                    value={formData.category}
                    onChange={handleChange}
                    required
                    className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring"
                >
                    <option value="">Select a category</option>
                    <option value="Cultural">Cultural</option>
                    <option value="Technical">Technical</option>
                    <option value="Hybrid">Hybrid</option>
                </select>
            </div>


          <Button type="submit">Add Club</Button>

          {status === 'success' && <p className="text-green-600 mt-2">✅ Club added successfully!</p>}
          {status === 'error' && <p className="text-red-600 mt-2">❌ Failed to add club.</p>}
        </form>
      </CardContent>
    </Card>
  );
}
