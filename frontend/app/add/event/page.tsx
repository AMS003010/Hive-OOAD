'use client';

import { useState } from 'react';
import { Input } from '@/components/ui/input';
import { Textarea } from '@/components/ui/textarea';
import { Button } from '@/components/ui/button';
import { Card, CardContent } from '@/components/ui/card';
import { Label } from '@/components/ui/label';

export default function AddEventForm() {
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    start_date: '',
    end_date: '',
    location: '',
    created_by: '',
    by_club: '',
    vol_count: '',
    org_count: '',
    max_participants: '',
    curr_participants: '',
    budget: '',
    first_contact: '',
    sec_contact: '',
  });

  const [status, setStatus] = useState<'idle' | 'success' | 'error'>('idle');

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8000/api/event', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          ...formData,
          vol_count: parseInt(formData.vol_count),
          org_count: parseInt(formData.org_count),
          max_participants: parseInt(formData.max_participants),
          curr_participants: parseInt(formData.curr_participants),
          budget: parseInt(formData.budget),
        }),
      });

      setStatus(response.ok ? 'success' : 'error');
    } catch {
      setStatus('error');
    }
  };

  return (
    <Card className="max-w-3xl mx-auto mt-10 shadow-md">
      <CardContent className="p-6 space-y-4">
        <h2 className="text-2xl font-bold mb-4">Add a New Event</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <Label htmlFor="name">Event Name</Label>
            <Input name="name" value={formData.name} onChange={handleChange} required />
          </div>

          <div>
            <Label htmlFor="description">Description</Label>
            <Textarea name="description" value={formData.description} onChange={handleChange} required />
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <Label htmlFor="start_date">Start Date</Label>
              <Input type="datetime-local" name="start_date" value={formData.start_date} onChange={handleChange} required />
            </div>
            <div>
              <Label htmlFor="end_date">End Date</Label>
              <Input type="datetime-local" name="end_date" value={formData.end_date} onChange={handleChange} required />
            </div>
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <Label htmlFor="location">Location</Label>
              <Input name="location" value={formData.location} onChange={handleChange} required />
            </div>
            <div>
              <Label htmlFor="by_club">Club</Label>
              <Input name="by_club" value={formData.by_club} onChange={handleChange} required />
            </div>
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <Label htmlFor="created_by">Created By</Label>
              <Input name="created_by" value={formData.created_by} onChange={handleChange} required />
            </div>
            <div>
              <Label htmlFor="budget">Budget</Label>
              <Input type="number" name="budget" value={formData.budget} onChange={handleChange} required />
            </div>
          </div>

          <div className="grid grid-cols-3 gap-4">
            <div>
              <Label htmlFor="vol_count">Volunteers</Label>
              <Input type="number" name="vol_count" value={formData.vol_count} onChange={handleChange} required />
            </div>
            <div>
              <Label htmlFor="org_count">Organizers</Label>
              <Input type="number" name="org_count" value={formData.org_count} onChange={handleChange} required />
            </div>
            <div>
              <Label htmlFor="max_participants">Max Participants</Label>
              <Input type="number" name="max_participants" value={formData.max_participants} onChange={handleChange} required />
            </div>
          </div>

          <div>
            <Label htmlFor="curr_participants">Current Participants</Label>
            <Input type="number" name="curr_participants" value={formData.curr_participants} onChange={handleChange} required />
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <Label htmlFor="first_contact">First Contact</Label>
              <Input name="first_contact" value={formData.first_contact} onChange={handleChange} required />
            </div>
            <div>
              <Label htmlFor="sec_contact">Second Contact</Label>
              <Input name="sec_contact" value={formData.sec_contact} onChange={handleChange} required />
            </div>
          </div>

          <Button type="submit">Add Event</Button>

          {status === 'success' && <p className="text-green-600 mt-2">✅ Event added successfully!</p>}
          {status === 'error' && <p className="text-red-600 mt-2">❌ Failed to add event.</p>}
        </form>
      </CardContent>
    </Card>
  );
}
