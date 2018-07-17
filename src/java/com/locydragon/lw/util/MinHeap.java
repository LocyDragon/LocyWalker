package com.locydragon.lw.util;


import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MinHeap
{
	private final ArrayList<Data> queue = new ArrayList<>();
	private int endPnt = 0;
	private final Map<String, Data> index = new HashMap<>();

	public Data getAndRemoveMin()
	{
		if (isEmpty())
		{
			return null;
		}

		Data head = queue.get(0);
		Data last = queue.get(endPnt - 1);
		queue.set(0, last);
		endPnt--;
		index.remove(getKey(head.loc));

		topDown();

		return head;
	}

	public Data find(Location pnt)
	{
		return index.get(getKey(pnt));
	}

	public void add(Data data)
	{
		if (queue.size() > endPnt)
		{
			queue.set(endPnt, data);
		}
		else
		{
			queue.add(data);
		}
		endPnt++;

		index.put(getKey(data.loc), data);

		bottomUp();
	}

	public boolean isEmpty()
	{
		return endPnt <= 0;
	}

	private String getKey(Location pnt)
	{
		return String.format("%d-%d", pnt.getBlockX(), pnt.getBlockZ());
	}

	private void topDown()
	{
		for (int cur = 0; cur < endPnt; )
		{
			int left  = 2 * cur + 1;
			int right = 2 * cur + 2;

			Data dc = queue.get(cur);
			Data dl = left < endPnt ? queue.get(left) : null;
			Data dr = right < endPnt ? queue.get(right) : null;

			int next = -1;
			Data dn = dc;
			if (dl != null && dl.g + dl.h < dn.g + dn.h)
			{
				next = left;
				dn = dl;
			}
			if (dr != null && dl.g + dl.h < dn.g + dn.h)
			{
				next = right;
				dn = dr;
			}

			if (next >= 0 && next < endPnt)
			{
				queue.set(next, dc);
				queue.set(cur, dn);
				cur = next;
			}
			else
			{
				break;
			}
		}


	}

	private void bottomUp()
	{
		for (int cur = endPnt - 1; cur >= 0; )
		{
			int parent = (cur - 1) / 2;
			if (parent < 0)
			{
				break;
			}

			Data dc = queue.get(cur);
			Data dp = queue.get(parent);

			if (dc.g + dc.h < dp.g + dc.h)
			{
				queue.set(parent, dc);
				queue.set(cur, dp);
				cur = parent;
			}
			else
			{
				break;
			}
		}
	}
}