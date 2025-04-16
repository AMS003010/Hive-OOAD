'use client';

import { useState } from 'react';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { Card, CardContent } from '@/components/ui/card';
import { Label } from '@/components/ui/label';

export default function AddParticipantForm() {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    phno: '',
    registration_status: '',
    event: '',
  });

  const [status, setStatus] = useState<'idle' | 'success' | 'error'>('idle');

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8000/api/participant', {
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
    <Card className="max-w-xl mx-auto mt-10 shadow-md">
      <CardContent className="p-6 space-y-4">
        <h2 className="text-2xl font-bold mb-4">Add a New Participant</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <Label htmlFor="name">Name</Label>
            <Input name="name" value={formData.name} onChange={handleChange} required />
          </div>

          <div>
            <Label htmlFor="email">Email</Label>
            <Input type="email" name="email" value={formData.email} onChange={handleChange} required />
          </div>

          <div>
            <Label htmlFor="phno">Phone Number</Label>
            <Input name="phno" value={formData.phno} onChange={handleChange} required />
          </div>

          <div>
            <Label htmlFor="registration_status">Registration Status</Label>
            <select
              name="registration_status"
              value={formData.registration_status}
              onChange={handleChange}
              required
              className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring"
            >
              <option value="">Select status</option>
              <option value="Pending">Pending</option>
              <option value="Approved">Approved</option>
              <option value="Rejected">Rejected</option>
            </select>
          </div>

          <div>
            <Label htmlFor="event">Event Name</Label>
            <Input name="event" value={formData.event} onChange={handleChange} required />
          </div>

          <Button type="submit">Add Participant</Button>

          {status === 'success' && <p className="text-green-600 mt-2">✅ Participant added successfully!</p>}
          {status === 'error' && <p className="text-red-600 mt-2">❌ Failed to add participant.</p>}
        </form>
      </CardContent>
    </Card>
  );
}
